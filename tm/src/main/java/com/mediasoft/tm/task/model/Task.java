package com.mediasoft.tm.task.model;

import com.mediasoft.tm.contributor.model.Contributor;
import com.mediasoft.tm.task.model.stmach.TaskState;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contributor_task",
            joinColumns = @JoinColumn(name = "task_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "contributor_id", nullable = false))
    private List<Contributor> contributors;

    @Column(nullable = false)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @Column(name = "status",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskState state;

    private Date startDate;

    private Date finishDate;
}
