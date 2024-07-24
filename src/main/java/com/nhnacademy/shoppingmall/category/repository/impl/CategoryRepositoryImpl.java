package com.nhnacademy.shoppingmall.category.repository.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public Optional<Category> findByCategoryId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        log.debug("sql: {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Category(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            Objects.isNull(rs.getObject("parent_category_id")) ? 0 : rs.getInt("parent_category_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Category> findByCategoryName(String categoryName) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM categories WHERE category_name = ?";
        log.debug("sql: {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Category(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            Objects.isNull(rs.getObject("parent_category_id")) ? 0 : rs.getInt("parent_category_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Category> findRootCategories() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM categories WHERE parent_category_id IS NULL";
        log.debug("sql: {}", sql);
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while(rs.next()) {
                categories.add(new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        Objects.isNull(rs.getObject("parent_category_id")) ? 0 : rs.getInt("parent_category_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    @Override
    public List<Category> findChildrenByCategoryId(int parentCategoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM categories WHERE parent_category_id = ?";
        log.debug("sql: {}", sql);
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, parentCategoryId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    categories.add(new Category(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            Objects.isNull(rs.getObject("parent_category_id")) ? 0 : rs.getInt("parent_category_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    @Override
    public int save(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO categories (category_name, parent_category_id) VALUES (?, ?)";
        log.debug("sql: {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, category.getCategoryName());
            if(category.getParentCategoryId() == 0) {
                preparedStatement.setObject(2, null);
            } else {
                preparedStatement.setInt(2, category.getParentCategoryId());
            }

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByCategoryId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM categories WHERE category_id = ?";
        log.debug("sql: {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update categories set category_name=?, parent_category_id=? where category_id = ?";
        log.debug("sql: {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setInt(2, category.getParentCategoryId());
            preparedStatement.setInt(3, category.getCategoryId());

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByCategoryId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COUNT(*) FROM categories WHERE category_id = ?";
        log.debug("sql: {}", sql);
        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public int getLastCategoryId() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT MAX(category_id) FROM categories";
        log.debug("sql: {}", sql);
        int categoryId = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                categoryId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoryId;
    }
}
