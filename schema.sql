CREATE TABLE member
(
    member_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nickname          VARCHAR(255) NOT NULL,
    profile_image_url VARCHAR(255),
    oauth_id          VARCHAR(255) NOT NULL,
    oauth_provider    VARCHAR(255) NOT NULL,
    role              VARCHAR(255) NOT NULL,
    created_dt        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_dt        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE wishlist
(
    wishlist_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(50) NOT NULL,
    image_url   VARCHAR(255),
    member_id   BIGINT      NOT NULL,
    created_dt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_dt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE track
(
    track_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    artist_name VARCHAR(255) NOT NULL,
    title       VARCHAR(255) NOT NULL,
    album_name  VARCHAR(255),
    image_url   VARCHAR(255),
    wishlist_id BIGINT       NOT NULL,
    created_dt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_dt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_wishlist FOREIGN KEY (wishlist_id) REFERENCES wishlist (wishlist_id)
);