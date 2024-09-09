package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.entities.Feedback;

import java.util.List;

public interface FeedbackDao {

    void insert(Feedback obj);
    void update(Feedback obj);
    void deleteById(Integer id);
    Feedback findById(Integer id);
    List<Feedback> findAllByEventId(Integer id);

}
