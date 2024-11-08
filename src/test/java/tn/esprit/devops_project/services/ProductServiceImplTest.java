package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testaddProduct() {
// Créez un objet Stock fictif pour le test
        Stock mockStock = new Stock();
        mockStock.setIdStock(1L);

        // Créez un objet Product fictif pour le test
        Product mockProduct = new Product();
        mockProduct.setIdProduct(1L);
        mockProduct.setTitle("Mock Product");
        mockProduct.setPrice(10.0f);
        mockProduct.setQuantity(5);
        mockProduct.setCategory(ProductCategory.ELECTRONICS);
        mockProduct.setStock(mockStock);

        // Configurez le comportement du mock
        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(mockStock));
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        // Appelez la méthode à tester
        Product result = productService.addProduct(mockProduct, 1L);

        // Vérifiez le résultat
        assertEquals(mockProduct, result);

        // Vérifiez que les méthodes du mock ont été appelées
        verify(stockRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testretrieveProduct() {
        // Créez un objet Product fictif pour le test
        Product mockProduct = new Product();
        mockProduct.setIdProduct(1L);
        mockProduct.setTitle("Mock Product");
        mockProduct.setPrice(10.0f);
        mockProduct.setQuantity(5);
        mockProduct.setCategory(ProductCategory.ELECTRONICS);

        // Configurez le comportement du mock
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(mockProduct));

        // Appelez la méthode à tester
        Product result = productService.retrieveProduct(1L);

        // Vérifiez le résultat
        assertEquals(mockProduct, result);

        // Vérifiez que la méthode du mock a été appelée
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    void testretreiveAllProduct() {
        // Créez une liste de produits fictifs pour le test
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "Product 1", 10.0f, 5, ProductCategory.ELECTRONICS, null));
        mockProducts.add(new Product(2L, "Product 2", 20.0f, 8, ProductCategory.CLOTHING, null));

        // Configurez le comportement du mock
        when(productRepository.findAll()).thenReturn(mockProducts);

        // Appelez la méthode à tester
        List<Product> result = productService.retreiveAllProduct();

        // Vérifiez le résultat
        assertEquals(mockProducts, result);

        // Vérifiez que la méthode du mock a été appelée
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testretrieveProductByCategory() {
        // Créez une liste de produits fictifs pour le test
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "Product 1", 10.0f, 5, ProductCategory.ELECTRONICS, null));
        mockProducts.add(new Product(2L, "Product 2", 20.0f, 8, ProductCategory.ELECTRONICS, null));

        // Configurez le comportement du mock
        when(productRepository.findByCategory(ProductCategory.ELECTRONICS)).thenReturn(mockProducts);

        // Appelez la méthode à tester
        List<Product> result = productService.retrieveProductByCategory(ProductCategory.ELECTRONICS);

        // Vérifiez le résultat
        assertEquals(mockProducts, result);

        // Vérifiez que la méthode du mock a été appelée
        verify(productRepository, times(1)).findByCategory(ProductCategory.ELECTRONICS);

    }

    @Test
    void testdeleteProduct() {
        // Appelez la méthode à tester
        productService.deleteProduct(1L);

        // Vérifiez que la méthode du mock a été appelée
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testretreiveProductStock() {
        // Créez une liste de produits fictifs pour le test
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "Product 1", 10.0f, 5, ProductCategory.ELECTRONICS, null));
        mockProducts.add(new Product(2L, "Product 2", 20.0f, 8, ProductCategory.CLOTHING, null));

        // Configurez le comportement du mock
        when(productRepository.findByStockIdStock(anyLong())).thenReturn(mockProducts);

        // Appelez la méthode à tester
        List<Product> result = productService.retreiveProductStock(1L);

        // Vérifiez le résultat
        assertEquals(mockProducts, result);

        // Vérifiez que la méthode du mock a été appelée
        verify(productRepository, times(1)).findByStockIdStock(1L);
    }
}