package com.example.demo.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registrations")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Registration extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date expiryDate;

    @Column(name="user_id")
    private Integer userId;

    @JoinColumn(name="user_id",nullable = false, insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name="book_id")
    private Integer bookId;

    @JoinColumn(name = "book_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
}
