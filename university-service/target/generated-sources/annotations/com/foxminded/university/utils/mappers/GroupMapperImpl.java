package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.request.GroupDTO;
import com.foxminded.university.model.entity.Group;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-03T07:19:34+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class GroupMapperImpl implements GroupMapper {

    @Override
    public GroupDTO toDto(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDTO.GroupDTOBuilder groupDTO = GroupDTO.builder();

        groupDTO.id( group.getId() );
        groupDTO.name( group.getName() );

        return groupDTO.build();
    }

    @Override
    public Group toEntity(GroupDTO groupDTO) {
        if ( groupDTO == null ) {
            return null;
        }

        Group.GroupBuilder group = Group.builder();

        group.id( groupDTO.getId() );
        group.name( groupDTO.getName() );

        return group.build();
    }
}
