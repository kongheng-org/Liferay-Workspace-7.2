package com.liferay.docs.guestbook.search;

public interface GuestbookEntryBatchReindexer {
    void reindex(long guestbookId, long companyId);
}
