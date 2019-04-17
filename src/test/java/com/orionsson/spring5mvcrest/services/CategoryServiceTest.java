package com.orionsson.spring5mvcrest.services;

import com.orionsson.spring5mvcrest.api.v1.mapper.CategoryMapper;
import com.orionsson.spring5mvcrest.api.v1.model.CategoryDTO;
import com.orionsson.spring5mvcrest.domain.Category;
import com.orionsson.spring5mvcrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {
    private static final Long ID = 2L;
    private static final String NAME = "rcp";

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    public void testGetAllCategories() throws Exception{
        //given
        List<Category> categories = Arrays.asList(new Category(),new Category(),new Category());
        when(categoryRepository.findAll()).thenReturn(categories);
        //when
        List<CategoryDTO> categoriesDTOS = categoryService.getAllCategories();
        //then
        assertEquals(3,categoriesDTOS.size());
    }

    @Test
    public void testGetCategoryName() throws Exception{
        //given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);
        when(categoryRepository.findByName(anyString())).thenReturn(category);
        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);
        //then
        assertEquals(ID,categoryDTO.getId());
    }
}