package fr.fms.hotel.web;

import fr.fms.hotel.doa.CustomerRepository;
import fr.fms.hotel.doa.OrderItemRepository;
import fr.fms.hotel.doa.OrderRepository;
import fr.fms.hotel.doa.RoomRepository;
import fr.fms.hotel.entities.Customer;
import fr.fms.hotel.entities.Order;
import fr.fms.hotel.entities.OrderItem;
import fr.fms.hotel.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@CrossOrigin("*")
@RestController
public class OrderController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    @PostMapping("/order")
    public Order saveOrder(@RequestBody Order order){
        Customer customer = new Customer();
        customer.setFirstName(order.getCustomer().getFirstName());
        customer.setLastname(order.getCustomer().getLastname());
        customer.setAddress(order.getCustomer().getAddress());
        customer.setPhone(order.getCustomer().getPhone());
        customer.setEmail(order.getCustomer().getEmail());
        customer = customerRepository.save(customer);       //sauvegarde du client

        Order orderLocal = new Order();
        orderLocal.setCustomer(customer);
        orderLocal.setDate(new Date());
        orderLocal.setAmount(order.getAmount());                 //ToDo on doit refaire le calcul côté back
        orderLocal = orderRepository.save(orderLocal);           //sauvegarde de la commande

        for(OrderItem oi : order.getRooms()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setName(oi.getName());
            orderItem.setDay(oi.getDay());
            orderItem.setPrice(oi.getPrice());
            Room room = roomRepository.findById(oi.getId()).get();
            orderItem.setRoom(room);
            orderItem.setOrder(orderLocal);
            orderItemRepository.save(orderItem);
        }
        return orderLocal;
    }
}

