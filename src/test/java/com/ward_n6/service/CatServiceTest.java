package com.ward_n6.service;
import com.ward_n6.entity.pets.Cat_2;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.repository.CatsCrud;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

public class CatServiceTest {
    private CatService catService;

    @Mock
    private CatsCrud catsCrud;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        catService = new CatService(catsCrud);
    }

    @Test
    public void testAddCat() {
        PetsSex petsSex = PetsSex.MALE;
        Cat_2 cat_2 = new Cat_2();

        Cat_2 savedCat = new Cat_2();
        savedCat.setPetsSex(petsSex);

        when(catsCrud.save(cat_2)).thenReturn(savedCat);

        catService.addCat(petsSex, cat_2);

        verify(catsCrud).save(cat_2);
        assertEquals(petsSex, cat_2.getPetsSex());
    }

    @Test
    public void testFindCat() {
        long id = 1;
        Cat_2 cat_2 = new Cat_2();
        Optional<Cat_2> optionalCat_2 = Optional.of(cat_2);

        when(catsCrud.findById(id)).thenReturn(Optional.of(cat_2));

        Optional<Cat_2> result = Optional.ofNullable(catService.findCat(id));

        assertEquals(optionalCat_2, result);
    }

    @Test
    public void testDeleteCat() {
        long id = 1;
        Assertions.assertNull(catService.deleteCat(id));

    }


    @Test
    public void testAllCats() {
        List<Cat_2> expectedCats = new ArrayList<>();
        Cat_2 cat1 = new Cat_2();
        Cat_2 cat2 = new Cat_2();
        expectedCats.add(cat1);
        expectedCats.add(cat2);
        when(catsCrud.findAll()).thenReturn(expectedCats);
        List<Cat_2> actualCats = catService.allCats();

       assertEquals(expectedCats.size(), actualCats.size());
            for (int i = 0; i < expectedCats.size(); i++) {
                assertEquals(expectedCats.get(i), actualCats.get(i));
        }
    }

    @Test
    public void testChange() {
        long id = 1;
        Cat_2 cat_2 = new Cat_2();
        PetsSex petsSex = PetsSex.FEM;

        Cat_2 expected = new Cat_2();
        expected.setId(id);
        expected.setPetsSex(petsSex);

        when(catsCrud.save(cat_2)).thenReturn(expected);

        Cat_2 result = catService.change(id, cat_2, petsSex);

        assertEquals(expected, result);
    }
}