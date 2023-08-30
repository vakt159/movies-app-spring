package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.persistence.DistributorDAO;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Data
public class DistributorServiceImpl  implements DistributorService{

    private DistributorDAO distributorDAO;

    public DistributorServiceImpl(DistributorDAO distributorDAO) {
        this.distributorDAO = distributorDAO;
    }

    @Override
    public List<Distributor> findAll() {

        return distributorDAO.findAll();
    }

    @Override
    @Transactional
    public void deleteDistributorById(Long id) {

        distributorDAO.deleteById(id);
    }

    @Override
    public Distributor findById(Long id) {
        Optional<Distributor> dOptional = distributorDAO.findById(id);
        if(dOptional.isPresent()){
            return (Distributor) dOptional.get();
        }else{
            throw new NoSuchElementException("Distributor with id = " + id + " is not found");
        }
    }

    @Override
    public Distributor findByName(String name) {
        Optional<Distributor> dOptional = distributorDAO.findDistributorByName(name);
        if(dOptional.isPresent()){
            return (Distributor) dOptional.get();
        }else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public Distributor save(Distributor distributor) {
        return distributorDAO.save(distributor);
    }
}
