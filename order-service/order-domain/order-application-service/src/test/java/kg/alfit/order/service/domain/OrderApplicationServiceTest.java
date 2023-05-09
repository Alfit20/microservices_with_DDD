package kg.alfit.order.service.domain;

import kg.alfit.domain.valueobject.*;
import kg.alfit.order.service.domain.dto.create.request.CreateOrderCommand;
import kg.alfit.order.service.domain.dto.create.request.OrderAddress;
import kg.alfit.order.service.domain.dto.create.response.CreateOrderResponse;
import kg.alfit.order.service.domain.entity.*;
import kg.alfit.order.service.domain.exception.OrderDomainException;
import kg.alfit.order.service.domain.mapper.OrderDataMapper;
import kg.alfit.order.service.domain.ports.input.service.OrderApplicationService;
import kg.alfit.order.service.domain.ports.output.repository.CustomerRepository;
import kg.alfit.order.service.domain.ports.output.repository.OrderRepository;
import kg.alfit.order.service.domain.ports.output.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;

    private final UUID CUSTOMER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
    private final UUID RESTAURANT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45");
    private final UUID PRODUCT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48");
    private final UUID ORDER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb50");
    private final BigDecimal PRICE = new BigDecimal("200.00");


    @BeforeAll
    public void init() {
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(1)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("50.00")))
                                .build(),
                        OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build()
                ))
                .build();
        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("250"))
                .items(List.of(OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(1)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("50.00")))
                                .build(),
                        OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build()
                ))
                .build();

        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(1)
                                .price(new Money(new BigDecimal("60.00")))
                                .subTotal(new Money(new BigDecimal("60.00")))
                                .build(),
                        OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build()
                ))
                .build();
        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurantResponse = Restaurant.Builder.builder()
                .id(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(
                                new Product(
                                        new ProductId(PRODUCT_ID), "product-1",
                                        new Money(new BigDecimal("50.00"))
                                ),
                                new Product(new ProductId(PRODUCT_ID), "product-2",
                                        new Money(new BigDecimal("50.00")))
                        )
                )
                .active(true)
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(
                orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void testCreateOrder() {
        CreateOrderResponse order = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
        assertEquals("Order created successfully", order.getMessage());
        assertNotNull(order.getOrderTrackingId());
    }

    @Test
    public void testCreateOrderWithWrongTotalPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongPrice));
        assertEquals("Total price: 250,00 " +
                "is not equal to Order items total: 200,00!", orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithWrongProductPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongProductPrice));
        assertEquals("Order item price: " +
                "60,00 is not valid for product " + PRODUCT_ID, orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithPassiveRestaurant() {
        Restaurant restaurant = Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(
                                new Product(
                                        new ProductId(PRODUCT_ID), "product-1",
                                        new Money(new BigDecimal("50.00"))
                                ),
                                new Product(new ProductId(PRODUCT_ID), "product-2",
                                        new Money(new BigDecimal("50.00")))
                        )
                )
                .active(false)
                .build();
        when(restaurantRepository.findRestaurantInformation(
                orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurant));
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommand));
        assertEquals("Restaurant with id " + RESTAURANT_ID +
                " is currently not active!", orderDomainException.getMessage());
    }


}
