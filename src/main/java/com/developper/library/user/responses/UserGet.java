package com.developper.library.user.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGet {
    private String username;
    private List<BookGetTitleAndId> books;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookGetTitleAndId {
        private String title;
        private UUID id;
    }
}
