package com.mjc.school.repository.implementation.dao;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.HibernateUtil;
import com.mjc.school.repository.implementation.model.AuthorModel;
import com.mjc.school.repository.implementation.model.NewsModel;
import com.mjc.school.repository.implementation.model.TagModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class NewsRepository implements BaseRepository<NewsModel, Long> {

    CriteriaBuilder criteriaBuilder = HibernateUtil.getSessionFactory().getCriteriaBuilder();

    @Override
    public List<NewsModel> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from NewsModel order by id asc", NewsModel.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(NewsModel.class, id));
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public NewsModel create(NewsModel entity) {
        Transaction tx = null;
        Long generatedId = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            generatedId = (Long) session.save(entity);
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return readById(generatedId).get();
    }

    @Override
    public NewsModel update(NewsModel entity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            entity.setCreateDate(readById(entity.getId()).get().getCreateDate());
            session.update(entity);
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return readById(entity.getId()).get();
    }


    @Override
    public boolean deleteById(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            NewsModel model = readById(id).get();
            session.delete(model);
            session.flush();
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<NewsModel> models = session.createQuery("from NewsModel where id = " + id, NewsModel.class).list();
            for (int i = 0; i < models.size(); i++) {
                if (models.get(i).getId() == id) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<NewsModel> getNewsByParams(String tagName, List<Long> tagIds, String authorName, String title, String content) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<NewsModel> criteriaQuery = criteriaBuilder.createQuery(NewsModel.class);
        Root<NewsModel> root = criteriaQuery.from(NewsModel.class);

        List<Predicate> predicates = new ArrayList<>();

        if (tagName != null || tagIds != null) {
            Join<TagModel, NewsModel> newsTags = root.join("tagsId");
            if (tagName != null) {
                predicates.add(newsTags.get("name").in(tagName));
            }
            if (tagIds != null) {
                predicates.add(newsTags.get("id").in(tagIds));
            }
        }
        if (authorName != null) {
            Join<AuthorModel, NewsModel> newsAuthor = root.join("author");
            predicates.add(criteriaBuilder.like(newsAuthor.get("name"), "%" + authorName + "%"));
        }
        if (title != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
        }
        if (content != null) {
            predicates.add(criteriaBuilder.like(root.get("content"), "%" + content + "%"));
        }
        criteriaQuery.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));

        Query employeeQuery = session.createQuery(criteriaQuery);
        List<NewsModel> result = employeeQuery.getResultList();
        session.close();

        return result;
    }
}
