package com.example.alexandria.service;


import com.example.alexandria.entity.Publisher;
import com.example.alexandria.repository.PublisherRepository;
import com.example.alexandria.service.exception.PublisherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;
    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher findByIdPublisherService(Long id) throws PublisherNotFoundException {
        return publisherRepository.findById(id)
                .orElseThrow(PublisherNotFoundException::new);
    }

    public List<Publisher> findAllPublisherService() throws PublisherNotFoundException {
        return publisherRepository.findAll();
    }

    public Publisher createPublisherService(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisherService(Long id, Publisher publisher) throws PublisherNotFoundException {
        Publisher publisherFromDb = findByIdPublisherService(id);

        publisherFromDb.setName(publisher.getName());
        publisherFromDb.setAddress(publisher.getAddress());

        return publisherRepository.save(publisherFromDb);

    }

    public Publisher deleteByIdPublisherService(Long id) throws PublisherNotFoundException {
        Publisher publisher = findByIdPublisherService(id);
        publisherRepository.deleteById(id);
        return publisher;
    }

}
