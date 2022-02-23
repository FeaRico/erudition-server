package ru.mahach.eruditionserver.models.dto;

import java.util.Objects;

public class ItemDto {
    private Long id;
    private String name;
    private String imagePath;

    public ItemDto() {
    }

    public ItemDto(Long id, String name, String imagePath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return id.equals(itemDto.id) && name.equals(itemDto.name) && Objects.equals(imagePath, itemDto.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imagePath);
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
