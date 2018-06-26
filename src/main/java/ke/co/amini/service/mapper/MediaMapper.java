package ke.co.amini.service.mapper;

import ke.co.amini.domain.*;
import ke.co.amini.service.dto.MediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Media and its DTO MediaDTO.
 */
@Mapper(componentModel = "spring", uses = {AlbumMapper.class})
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "album.name", target = "albumName")
    MediaDTO toDto(Media media);

    @Mapping(source = "albumId", target = "album")
    Media toEntity(MediaDTO mediaDTO);

    default Media fromId(Long id) {
        if (id == null) {
            return null;
        }
        Media media = new Media();
        media.setId(id);
        return media;
    }
}
