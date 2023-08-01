package lezhin.coding.global.event.content;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentEvent {

    @Getter
    public static class ContentHistory {

        private Long contentId;

        public static ContentHistory of(Long contentId) {
            return new ContentHistory(contentId);
        }

        public ContentHistory(final Long contentId) {
            this.contentId = contentId;
        }
    }

}
