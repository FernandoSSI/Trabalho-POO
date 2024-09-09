package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.dao.FeedbackDao;
import com.example.gestaodeeventos.model.entities.Feedback;

import java.util.List;

public class FeedbackService {

    private FeedbackDao dao = DaoFactory.createFeedbackDao();

    public void saveOrUpdate(Feedback feedback) {
        if (feedback.getId() == null) {
            dao.insert(feedback);
        } else {
            dao.update(feedback);
        }
    }

    public void remove(Feedback feedback) {
        if (feedback.getId() != null) {
            dao.deleteById(feedback.getId());
        } else {
            throw new IllegalArgumentException("Feedback must have a valid ID to be deleted.");
        }
    }

    public Feedback findById(Integer id) {
        return dao.findById(id);
    }

    public List<Feedback> findAllByEventId(Integer id) {
        return dao.findAllByEventId(id);
    }
}
