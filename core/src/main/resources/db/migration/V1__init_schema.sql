CREATE TABLE user (
    id BINARY(16) NOT NULL PRIMARY KEY,
    social_id VARCHAR(255) NOT NULL,
    social_provider VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    image_url VARCHAR(1000) NOT NULL,
    role VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    UNIQUE INDEX uk_user_social (social_id, social_provider)
);

CREATE TABLE curriculum (
    id BINARY(16) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    image_url VARCHAR(1000) NOT NULL
);

CREATE TABLE course (
    id BINARY(16) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    curriculum_id BINARY(16) NOT NULL
);

CREATE TABLE chapter (
    id BINARY(16) NOT NULL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    document TEXT NOT NULL,
    course_id BINARY(16) NOT NULL,
    `index` INT NOT NULL
);

CREATE TABLE quiz (
    id BINARY(16) NOT NULL PRIMARY KEY,
    chapter_id BINARY(16) NOT NULL,
    question TEXT NOT NULL,
    solution TEXT NOT NULL
);

CREATE TABLE quiz_option (
    id BINARY(16) NOT NULL PRIMARY KEY,
    quiz_id BINARY(16) NOT NULL,
    content TEXT NOT NULL,
    is_answer BOOLEAN NOT NULL
);

CREATE TABLE quiz_bookmark (
    id BINARY(16) NOT NULL PRIMARY KEY,
    quiz_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    UNIQUE INDEX uk_quiz_bookmark_quiz_user (quiz_id, user_id)
);

CREATE TABLE quiz_reaction (
    id BINARY(16) NOT NULL PRIMARY KEY,
    quiz_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    reaction_type VARCHAR(50) NOT NULL,
    UNIQUE INDEX uk_quiz_reaction_quiz_user (quiz_id, user_id)
);

CREATE TABLE user_solved_quiz (
    id BINARY(16) NOT NULL PRIMARY KEY,
    user_id BINARY(16) NOT NULL,
    quiz_id BINARY(16) NOT NULL,
    selected_option_id BINARY(16),
    is_correct BOOLEAN NOT NULL,
    UNIQUE INDEX uk_user_solved_quiz_user_quiz (user_id, quiz_id)
);
