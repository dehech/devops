package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void addStock() {
        Stock stockToAdd = new Stock();
        stockToAdd.setTitle("Test Stock");

        // Mocking the behavior of stockRepository.save()
        when(stockRepository.save(any(Stock.class))).thenReturn(stockToAdd);

        // When
        Stock resultStock = stockService.addStock(stockToAdd);

        // Then
        assertNotNull(resultStock);
        assertEquals("Test Stock", resultStock.getTitle());

        // Verifying that save method was called on stockRepository
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    void retrieveStock() {
        Long stockId = 1L;
        Stock mockStock = new Stock();
        mockStock.setIdStock(stockId);
        mockStock.setTitle("Mock Stock");

        // Mocking the behavior of stockRepository.findById()
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(mockStock));

        // When
        Stock resultStock = stockService.retrieveStock(stockId);

        // Then
        assertNotNull(resultStock);
        assertEquals(stockId, resultStock.getIdStock());
        assertEquals("Mock Stock", resultStock.getTitle());

        // Verifying that findById method was called on stockRepository
        verify(stockRepository, times(1)).findById(stockId);

        // Verifying that exception is thrown when stock is not found
        assertThrows(NullPointerException.class, () -> stockService.retrieveStock(2L));
    }

    @Test
    void retrieveAllStock() {
        Stock mockStock = new Stock();
        mockStock.setIdStock(1L);
        mockStock.setTitle("Mock Stock");

        // Mocking the behavior of stockRepository.findAll()
        when(stockRepository.findAll()).thenReturn(Collections.singletonList(mockStock));

        // When
        List<Stock> resultStockList = stockService.retrieveAllStock();

        // Then
        assertNotNull(resultStockList);
        assertEquals(1, resultStockList.size());

        Stock resultStock = resultStockList.get(0);
        assertEquals(1L, resultStock.getIdStock());
        assertEquals("Mock Stock", resultStock.getTitle());

        // Verifying that findAll method was called on stockRepository
        verify(stockRepository, times(1)).findAll();
    }

}
