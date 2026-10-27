package org.example.data.service;

import com.google.protobuf.ByteString;
import generated.Album;
import generated.Date;
import generated.Image;
//import generated.categories;
//import generated.imageFileType;
import org.example.data.dto.ImageDataDTO;import org.example.data.dto.ItemListingDataDTO;
import org.example.data.dto.ItemListingResponseDTO;
import org.example.data.entities.Category;
import org.example.data.entities.ItemListing;
import org.example.data.entities.ItemPicture;
import org.example.data.entities.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.data.repository.CategoryRepository;
import org.example.data.repository.ItemListingRepository;
import org.example.data.repository.MemberRepository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;import static org.example.data.mappers.ItemListingDataMapper.toResponseDTO;

@Service
public class DataItemListingService {

    private static final Logger LOG = LoggerFactory.getLogger(DataItemListingService.class);

    private final ItemListingRepository itemListingRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final Path uploadDir = Paths.get("uploads");
    @Autowired
    public DataItemListingService(
            ItemListingRepository itemListingRepository,
            MemberRepository memberRepository,
            CategoryRepository categoryRepository) {
        this.itemListingRepository = itemListingRepository;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ItemListingResponseDTO addItem(ItemListingDataDTO dto) {
        //Protected methods are only accessible in the same package or in subclasses, I suggest DAO as part of instance variable,
        // and calling the method from that class,  DAO can use the repositories because it can be in the same package as the them(while service shouldn't be)
        ItemListing entity = new ItemListing();
        entity.setItemName(dto.itemName());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());

        // 1. Map Date (or default to today)
        // Received DTO from add message will never contain date fields, only responses do
        entity.setListDate(LocalDate.now());


        // 2. Find or create Member by Email
        String rawIdentifier = dto.memberName().isBlank()
                ? "wolf@cloverville.dk"
                : dto.memberName().trim();

        // If the client passed an email in memberName, use it; otherwise format as email
        String email = rawIdentifier.contains("@")
                ? rawIdentifier.toLowerCase()
                : rawIdentifier.toLowerCase().replaceAll("\\s+", ".") + "@cloverville.dk";

        //Adding a new item shouldn't create a Member in the future, but for now its fine to test
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(new Member(rawIdentifier, email, "default_password")));
        entity.setMember(member);

        // 3. Find or create Categories
        for (generated.Category protoCat : dto.categories()) {
            String catName = protoCat.name();
            Category category = categoryRepository.findByName(catName)
                    .orElseGet(() -> categoryRepository.save(new Category(catName)));
            entity.addCategory(category);
        }

        // 4. Map Album Images to ItemPicture with proper IOException handling
        if (dto.album() != null) {
            for (ImageDataDTO imageDTO : dto.album().images()) {
                //TODO resolve filetype check based on dto.filetype
                String fileName = imageDTO.name().isBlank()
                        ? "image_" + System.currentTimeMillis() + ".png"
                        : imageDTO.name();
                Path filePath = uploadDir.resolve(fileName);

                try {
                    if (imageDTO.data().length != 0) {
                        Files.createDirectories(uploadDir);
                        Files.write(filePath, imageDTO.data().toByteArray());
                    }
                } catch (IOException e) {
                    LOG.error("Failed to write image file '{}' to disk at {}", fileName, filePath.toAbsolutePath(), e);
                    throw new UncheckedIOException("Could not save image file: " + fileName, e);
                }

                ItemPicture picture = new ItemPicture(fileName, filePath.toString());
                entity.addPicture(picture);
            }
        }

        ItemListing savedEntity = itemListingRepository.save(entity);
        return toResponseDTO(savedEntity);
    }

    @Transactional(readOnly = true)
    public Optional<generated.ItemListing> getItemById(int id) {
        return itemListingRepository.findById(id).map(this::toProto);
    }

    public generated.ItemListing toProto(model.ItemListing entity) {
        LocalDate date = entity.getListDate() != null ? entity.getListDate() : LocalDate.now();
        Date protoDate = Date.newBuilder()
                .setYear(date.getYear())
                .setMonth(date.getMonthValue())
                .setDay(date.getDayOfMonth())
                .build();

        Album.Builder albumBuilder = Album.newBuilder()
                .setTitle(entity.getItemName() != null ? entity.getItemName() : "");

        for (ItemPicture pic : entity.getPictures()) {
            ByteString imageBytes = ByteString.empty();
            try {
                Path path = Paths.get(pic.getFilePath());
                if (Files.exists(path)) {
                    imageBytes = ByteString.copyFrom(Files.readAllBytes(path));
                } else {
                    LOG.warn("Image file does not exist on disk: {}", path.toAbsolutePath());
                }
            } catch (IOException e) {
                LOG.error("Failed to read image file from path: {}", pic.getFilePath(), e);
            }

            imageFileType type = pic.getFileName() != null && pic.getFileName().toLowerCase().endsWith(".jpg")
                    ? imageFileType.jpg
                    : imageFileType.png;

            Image protoImg = Image.newBuilder()
                    .setName(pic.getFileName() != null ? pic.getFileName() : "")
                    .setFiletype(type)
                    .setData(imageBytes)
                    .build();
            albumBuilder.addImages(protoImg);
        }

        generated.ItemListing.Builder builder = generated.ItemListing.newBuilder()
                .setId(entity.getItemId())
                .setItemName(entity.getItemName() != null ? entity.getItemName() : "")
                .setMemberName(entity.getMember() != null ? entity.getMember().getEmail() : "")
                .setDescription(entity.getDescription() != null ? entity.getDescription() : "")
                .setPrice(entity.getPrice())
                .setDate(protoDate)
                .setAlbum(albumBuilder.build());

        for (Category cat : entity.getCategories()) {
            try {
                builder.addCategories(categories.valueOf(cat.getName()));
            } catch (IllegalArgumentException e) {
                LOG.warn("Unknown category '{}' in database, skipping proto mapping", cat.getName());
            }
        }

        return builder.build();
    }
}