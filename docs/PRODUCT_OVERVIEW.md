# TeasLeisure Product Overview

TeasLeisure (TL) is a local-stall discovery and community product operated by a central platform team. Visitors browse stalls; users submit stall leads; merchants claim and manage stalls after review; users interact around stall posts; operators moderate content and accounts.

## Roles and flows

- **Visitor:** Browse published stalls, posts, and public comments; filter by city/area, category, distance, daily availability, and current business hours.
- **User:** Submit a stall lead, favorite stalls/posts, comment or reply, react, report content, and message other users.
- **Merchant:** Claim a stall through review, maintain its profile and daily status, and publish posts associated with their own approved stalls.
- **Platform operator:** Review stall submissions, claims, revisions, posts, comments, and reports; hide/restore content and inspect audit history.

## Business rules

- A merchant may publish only for a stall claimed by the current user that is published and not deleted. Personal standalone experience posts are outside the MVP.
- Hiding/deleting a stall removes its linked posts from public list, detail, comment, and reaction paths while retaining history.
- Posts do not expire automatically and have no renewal flow. Author deletion or moderation hide removes them from public views.
- Private messaging supports blocking, reporting, rate limits, unread indicators, and monotonic read cursors. Closed or blocked conversations cannot accept new messages; history remains available to conversation members.
- The first release focuses on discovery and community. Checkout, payment, delivery, group purchasing, and distribution stay out of scope.

## Architecture boundary

CRMEB provides the starting point for Java APIs, authentication, admin RBAC, upload adapters, and the multi-platform client. TL business areas are organized as account, stall, community, conversation, and moderation. The current product is a centrally operated platform; merchant-tenant isolation is not part of the MVP.

The source Figma and product decisions are kept in the owner's Notion/Figma workspace; public repository documentation contains only implementation requirements needed by contributors.