package com.orionsson.spring5mvcrest.api.v1.mapper;

import com.orionsson.spring5mvcrest.api.v1.model.CategoryDTO;
import com.orionsson.spring5mvcrest.domain.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryMapperTest {
    public static final String NAME = "ftm";
    private static final Long ID= 1L;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
    @Test
    public void categoryToCategoryTest() throws Exception{
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}