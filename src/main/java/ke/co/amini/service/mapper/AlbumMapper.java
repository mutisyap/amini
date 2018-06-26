package ke.co.amini.service.mapper;

import ke.co.amini.domain.*;
import ke.co.amini.service.dto.AlbumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Album and its DTO AlbumDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlbumMapper extends EntityMapper<AlbumDTO, Album> {



    default Album fromId(Long id) {
        if (id == null) {
            return null;
        }
        Album album = new Album();
        album.setId(id);
        return album;
    }
}
