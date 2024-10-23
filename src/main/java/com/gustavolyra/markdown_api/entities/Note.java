package com.gustavolyra.markdown_api.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] markDown;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] html;


}
