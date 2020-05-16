package com.tech.mkblogs.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.tech.mkblogs.user.dto.UserDTO;
import com.tech.mkblogs.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	@Mappings({
	      @Mapping(target="id", source="user.id"),
	      @Mapping(target="username", source="user.username"),
	      @Mapping(target="password", source="user.password"),
	      @Mapping(target="firstName", source="user.firstName"),
	      @Mapping(target="lastName", source="user.lastName")
	    })
	UserDTO toDTO(User user);
	
	@Mappings({
		@Mapping(target="id", source="dto.id"),
	      @Mapping(target="username", source="dto.username"),
	      @Mapping(target="password", source="dto.password"),
	      @Mapping(target="firstName", source="dto.firstName"),
	      @Mapping(target="lastName", source="dto.lastName")
	    })
	User toModel(UserDTO dto);
	
	@Mappings({
	      @Mapping(target="id", ignore = true),
	      @Mapping(target="username", source="dto.username"),
	      @Mapping(target="password", source="dto.password"),
	      @Mapping(target="firstName", source="dto.firstName"),
	      @Mapping(target="lastName", source="dto.lastName"),
	      @Mapping(target="createdBy", ignore = true),
	      @Mapping(target="createdName", ignore = true),
	      @Mapping(target="createdTs", ignore = true),
	      @Mapping(target="lastModifiedBy", ignore = true),
	      @Mapping(target="lastModifiedTs", ignore = true),
	      @Mapping(target="version", ignore = true)	      
	    })
	User toDTOForUpdate(UserDTO dto,@MappingTarget User model);
}