package cn.hc.jpa.service;

import cn.hc.jpa.entity.Customer;
import cn.hc.jpa.repositories.CustomerRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hc on 2017/7/2.
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public List<Customer> findAll(){
        return  repository.findAll();
    }
    public List<Customer> finAll(String query, Pageable pageable){
        return repository.findAll();
    }
    private Specification<Customer> getSpecification(String query) {
        return  new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<String> firstNamePath = root.get("firstName");
                Predicate firstNamePredicate = criteriaBuilder.like(firstNamePath, "%" + query + "%");

                Path<String> lastNamePath = root.get("firstName");
                Predicate lastNamePredicate = criteriaBuilder.like(lastNamePath, "%" + query + "%");

                predicateList.add(criteriaBuilder.or(firstNamePredicate,lastNamePredicate));

                criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        };
    }

    private Specification<Customer> getSpecification2(String query) {
        return  (Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)->{
         return null;
        };
    }
}
