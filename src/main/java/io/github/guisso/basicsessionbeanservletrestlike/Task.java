/*
 * The MIT License
 *
 * Copyright 2025 Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.guisso.basicsessionbeanservletrestlike;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A simple task
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 * @version 0.1
 * @since 0.1, Sep 19, 2025
 */
@Entity
//<editor-fold defaultstate="collapsed" desc="NamedQueries">
@NamedQueries({
    @NamedQuery(name = "Task.removeById", query = "DELETE FROM Task t WHERE t.id = :id")
})
//</editor-fold>
public class Task
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120, nullable = false, unique = true)
    @NotBlank
    @Size(min = 3, max = 120)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Task() {
        createdAt = LocalDateTime.now();
    }

    public Task(String description) {
        this();
        this.description = description;
    }

    public Task(Long id, String description) {
        this();
        this.id = id;
        this.description = description;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="hashCode/equals/toString">
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Task)) {
            return false;
        }

        return hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        return "Task{"
                + "id=" + id
                + ", description=" + description
                + ", createdAt=" + createdAt
                + '}';
    }
    //</editor-fold>

}
