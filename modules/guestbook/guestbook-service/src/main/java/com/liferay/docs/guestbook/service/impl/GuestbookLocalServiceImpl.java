package com.liferay.docs.guestbook.service.impl;

import com.liferay.docs.guestbook.service.base.GuestbookLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

@Component(
	property = "model.class.name=com.liferay.docs.guestbook.model.Guestbook",
	service = AopService.class
)
public class GuestbookLocalServiceImpl extends GuestbookLocalServiceBaseImpl {
	public Guestbook addGuestbook(long userId, String name,
								  ServiceContext serviceContext) throws PortalException {

		long groupId = serviceContext.getScopeGroupId();

		User user = userLocalService.getUserById(userId);

		Date now = new Date();

		validate(name);

		long guestbookId = counterLocalService.increment();

		Guestbook guestbook = guestbookPersistence.create(guestbookId);

		guestbook.setUuid(serviceContext.getUuid());
		guestbook.setUserId(userId);
		guestbook.setGroupId(groupId);
		guestbook.setCompanyId(user.getCompanyId());
		guestbook.setUserName(user.getFullName());
		guestbook.setCreateDate(serviceContext.getCreateDate(now));
		guestbook.setModifiedDate(serviceContext.getModifiedDate(now));
		guestbook.setName(name);
		guestbook.setExpandoBridgeAttributes(serviceContext);

		guestbookPersistence.update(guestbook);

		return guestbook;
	}

	public List<Guestbook> getGuestbooks(long groupId) {

		return guestbookPersistence.findByGroupId(groupId);
	}

	public List<Guestbook> getGuestbooks(long groupId, int start, int end,
										 OrderByComparator<Guestbook> obc) {

		return guestbookPersistence.findByGroupId(groupId, start, end, obc);
	}

	public List<Guestbook> getGuestbooks(long groupId, int start, int end) {

		return guestbookPersistence.findByGroupId(groupId, start, end);
	}

	public int getGuestbooksCount(long groupId) {

		return guestbookPersistence.countByGroupId(groupId);
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new GuestbookNameException();
		}
	}
}