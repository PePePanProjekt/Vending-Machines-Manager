package pp.project.vmm.endpoint.system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.system.service.dto.ItemDetailsDTO;
import pp.project.vmm.endpoint.system.service.dto.ItemSimpleDTO;

import static java.lang.Boolean.TRUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(SpringExtension.class)

class ItemServiceImplementationTest {
    @Mock
    ItemRepository itemRepository;
    @InjectMocks
    ItemServiceImplementation itemService;
    UUID id = UUID.randomUUID();
    @BeforeEach
    public void init()
    {
        given(itemRepository.findAll()).willReturn(prepareMockData());
        given(itemRepository.findById(id)).willReturn(prepareMockData2(id));
        given(itemRepository.existsById(id)).willReturn(TRUE);

    }

    @Test
    void shouldGetItemsTest() {
        // When
        List<ItemDetailsDTO> itemDetailsDTO = itemService.getItems();

        // Then
        assertThat(itemDetailsDTO, Matchers.hasSize(3));
    }

    @Test
    void shouldGetItemsTest2() {
        // When
        List<ItemDetailsDTO> itemDetailsDTO = itemService.getItems();

        // Then
        String nameItem = String.valueOf(itemDetailsDTO.get(0).getName());
        assertEquals(nameItem, "name1");
    }

    private List<Item> prepareMockData(){
        List<Item> item = new ArrayList<>();

        item.add(new Item("name1", 1));
        item.add(new Item("name2", 2));
        item.add(new Item("name3", 3));

        return item;
    }

    private Optional<Item> prepareMockData2(UUID id){
        Item item = new Item("name1", 1);
        return Optional.of(item);
    }


    @Test
    void shouldGetItemByIdTest() {
        // When
        ItemDetailsDTO itemDetailsDTO = itemService.getItemById(id);

        // Then
        String nameItem = itemDetailsDTO.getName();
        assertEquals(nameItem, "name1");
    }

    @Test
    void shouldUpdateItem() {
        // Given
        ItemSimpleDTO simpleDTO = new ItemSimpleDTO(id, "name1");
        Item item = new Item("item1", 10);
        given(itemRepository.existsById(simpleDTO.getId())).willReturn(true);
        given(itemRepository.findById(simpleDTO.getId())).willReturn(Optional.of(item));

        // When
        ResponseEntity<String> resEntity =  itemService.updateItem(simpleDTO);

        // Then
        verify(itemRepository, times(1)).existsById(simpleDTO.getId());
        verify(itemRepository, times(1)).findById(simpleDTO.getId());

        assertNotNull(resEntity);
        assertEquals(HttpStatus.OK, resEntity.getStatusCode());
        assertEquals("Successfully updated item", resEntity.getBody());
    }

    @Test
    public void shouldNotUpdateItem() {
        // Given
        ItemSimpleDTO simpleDTO = new ItemSimpleDTO(id, "item1");

        given(itemRepository.existsById(simpleDTO.getId())).willReturn(false);

        // When
        ResponseEntity<String> resEntity = itemService.updateItem(simpleDTO);

        // Then
        verify(itemRepository, times(1)).existsById(simpleDTO.getId());
        verify(itemRepository, never()).findById(simpleDTO.getId());
        verify(itemRepository, never()).save(any());

        assertNotNull(resEntity);
        assertEquals(HttpStatus.NOT_FOUND, resEntity.getStatusCode());
        assertEquals("Item of given id does not exist", resEntity.getBody());
    }

    @Test
    public void shouldAddItem() {
        // Given
        ItemSimpleDTO simpleDTO = new ItemSimpleDTO(id, "item1");
        Item item = new Item();
        item.setId(id);
        item.setName("item1");
        item.setAmountAvailable(0);

        given(itemRepository.save(any(Item.class))).willReturn(item);

        // When
        ResponseEntity<String> resEntity = itemService.addItem(simpleDTO);

        // Then
        assertNotNull(resEntity);
        assertEquals(HttpStatus.OK, resEntity.getStatusCode());
        assertEquals("Successfully created new item", resEntity.getBody());
    }

    @Test
    public void shouldNotAddItem() {
        // Given
        ItemSimpleDTO simpleDTO = new ItemSimpleDTO(id, "item1");
        Item item1 = new Item();
        item1.setId(null);
        item1.setName("Item1");
        given(itemRepository.save(any(Item.class))).willReturn(item1);

        // When
        ResponseEntity<String> resEntity = itemService.addItem(simpleDTO);

        // Then
        verify(itemRepository, times(1)).save(any(Item.class));

        assertNotNull(resEntity);
        assertEquals(HttpStatus.BAD_REQUEST, resEntity.getStatusCode());
        assertEquals("Could not create new item", resEntity.getBody());
    }

    @Test
    public void shouldDeleteItemById() {
        // Given
        Item item = new Item();
        item.setId(id);
        item.setName("Item1");

        when(itemRepository.existsById(id)).thenReturn(true);

        // When
        ResponseEntity<String> result = itemService.deleteItemById(id);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Successfully deleted item", result.getBody());
        verify(itemRepository, times(1)).deleteById(id);
    }

    @Test
    public void shouldNotDeleteItemById() {
        // Given
        given(itemRepository.existsById(id)).willReturn(false);

        // When
        ResponseEntity<String> result = itemService.deleteItemById(id);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Item of given id does not exist", result.getBody());
        verify(itemRepository, times(0)).deleteById(id);
    }
}