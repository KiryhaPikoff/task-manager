package com.mediasoft.tm.contributor.model;

import com.mediasoft.tm.account.model.Account;
import com.mediasoft.tm.project.model.Project;
import com.mediasoft.tm.task.model.Task;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Account account;

    @ManyToOne(optional = false)
    private Project project;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany()
    private List<Task> tasks;
}
