CREATE TABLE basic_model_categories (
    category VARCHAR(255) PRIMARY KEY
);

CREATE TABLE model_option_parent_categories (
    category VARCHAR(255) PRIMARY KEY
);

CREATE TABLE model_option_child_categories (
    category VARCHAR(255) PRIMARY KEY
);

CREATE TABLE hash_tag_categories (
    category VARCHAR(255) PRIMARY KEY
);

CREATE TABLE basic_models (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              name VARCHAR(255) NOT NULL,
                              category VARCHAR(255) NOT NULL,
                              CONSTRAINT fk_basic_models_basic_model_categories FOREIGN KEY (category) REFERENCES basic_model_categories (category) ON UPDATE CASCADE
);

CREATE TABLE detail_models (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               basic_model_id BIGINT NOT NULL,
                               displacement FLOAT NOT NULL,
                               fuel_efficiency FLOAT NOT NULL,
                               CONSTRAINT fk_detail_models_basic_models FOREIGN KEY (basic_model_id) REFERENCES basic_models (id) ON UPDATE CASCADE
);

CREATE TABLE trims (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       basic_model_id BIGINT NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       description TEXT NOT NULL,
                       image_url VARCHAR(255) NOT NULL,
                       max_price INT NOT NULL,
                       min_price INT NOT NULL,
                       CONSTRAINT fk_trims_basic_models FOREIGN KEY (basic_model_id) REFERENCES basic_models (id) ON UPDATE CASCADE
);

CREATE TABLE detail_trims (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              trim_id BIGINT NOT NULL,
                              detail_model_id BIGINT NOT NULL,
                              CONSTRAINT fk_detail_trims_trims FOREIGN KEY (trim_id) REFERENCES trims (id) ON UPDATE CASCADE,
                              CONSTRAINT fk_detail_trims_detail_models FOREIGN KEY (detail_model_id) REFERENCES detail_models (id) ON UPDATE CASCADE
);

CREATE TABLE model_options (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               model_id BIGINT NOT NULL,
                               name VARCHAR(255) NOT NULL,
                               parent_category VARCHAR(255),
                               child_category VARCHAR(255) NOT NULL,
                               image_url VARCHAR(255) NOT NULL,
                               description TEXT NOT NULL,
                               CONSTRAINT fk_model_options_basic_models FOREIGN KEY (model_id) REFERENCES basic_models (id) ON UPDATE CASCADE,
                               CONSTRAINT fk_model_options_model_options FOREIGN KEY (parent_category) REFERENCES model_option_parent_categories (category) ON UPDATE CASCADE,
                               CONSTRAINT fk_model_options_child_category FOREIGN KEY (child_category) REFERENCES model_option_child_categories (category) ON UPDATE CASCADE
);

CREATE TABLE detail_model_decision_options (
                                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                               detail_model_id BIGINT NOT NULL,
                                               model_option_id BIGINT NOT NULL,
                                               price INT NOT NULL,
                                               CONSTRAINT fk_detail_model_decision_options_detail_models FOREIGN KEY (detail_model_id) REFERENCES detail_models (id) ON UPDATE CASCADE,
                                               CONSTRAINT fk_detail_model_decision_options_model_options FOREIGN KEY (model_option_id) REFERENCES model_options (id) ON UPDATE CASCADE
);

CREATE TABLE detail_trim_options (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     detail_trim_id BIGINT NOT NULL,
                                     model_option_id BIGINT NOT NULL,
                                     price INT NOT NULL,
                                     color_condition BOOLEAN NOT NULL,
                                     visibility BOOLEAN NOT NULL,
                                     basic BOOLEAN NOT NULL,
                                     CONSTRAINT fk_detail_trim_options_detail_trims FOREIGN KEY (detail_trim_id) REFERENCES detail_trims (id) ON UPDATE CASCADE,
                                     CONSTRAINT fk_detail_trim_options_model_options FOREIGN KEY (model_option_id) REFERENCES model_options (id) ON UPDATE CASCADE
);

CREATE TABLE detail_trim_packages (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      detail_trim_id BIGINT NOT NULL,
                                      name VARCHAR(255) NOT NULL,
                                      price INT NOT NULL,
                                      parent_category VARCHAR(255),
                                      color_condition BOOLEAN NOT NULL,
                                      image_url VARCHAR(255) NOT NULL,
                                      CONSTRAINT fk_detail_trim_packages_detail_trims FOREIGN KEY (detail_trim_id) REFERENCES detail_trims (id) ON UPDATE CASCADE,
                                      CONSTRAINT fk_detail_trim_packages_parent_category FOREIGN KEY (parent_category) REFERENCES model_option_parent_categories (category) ON UPDATE CASCADE
);

CREATE TABLE trim_package_options (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      trim_package_id BIGINT NOT NULL,
                                      detail_trim_option_id BIGINT NOT NULL,
                                      CONSTRAINT fk_trim_package_options_detail_trim_packages FOREIGN KEY (trim_package_id) REFERENCES detail_trim_packages (id) ON UPDATE CASCADE,
                                      CONSTRAINT fk_trim_package_options_detail_trim_options FOREIGN KEY (detail_trim_option_id) REFERENCES detail_trim_options (id) ON UPDATE CASCADE
);

CREATE TABLE hash_tags (
                           name VARCHAR(255) PRIMARY KEY,
                           category VARCHAR(255),
                           CONSTRAINT fk_hash_tags_category FOREIGN KEY (category) REFERENCES hash_tag_categories (category) ON UPDATE CASCADE
);

CREATE TABLE model_option_hash_tags (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        hash_tag VARCHAR(255) NOT NULL,
                                        model_option_id BIGINT NOT NULL,
                                        CONSTRAINT fk_model_option_hash_tags_hash_tags FOREIGN KEY (hash_tag) REFERENCES hash_tags (name) ON UPDATE CASCADE,
                                        CONSTRAINT fk_model_option_hash_tags_model_options FOREIGN KEY (model_option_id) REFERENCES model_options (id) ON UPDATE CASCADE
);

CREATE TABLE package_hash_tags (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   hash_tag VARCHAR(255) NOT NULL,
                                   package_id BIGINT NOT NULL,
                                   CONSTRAINT fk_package_hash_tags_hash_tags FOREIGN KEY (hash_tag) REFERENCES hash_tags (name) ON UPDATE CASCADE,
                                   CONSTRAINT fk_package_hash_tags_detail_trim_packages FOREIGN KEY (package_id) REFERENCES detail_trim_packages (id) ON UPDATE CASCADE
);

CREATE TABLE hmg_data(
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         model_option_id BIGINT NOT NULL,
                         name VARCHAR(255) NOT NULL,
                         `value` VARCHAR(255) NOT NULL,
                         measure VARCHAR(255) NOT NULL,
                         CONSTRAINT fk_hmg_tags_model_options FOREIGN KEY (model_option_id) REFERENCES model_options (id) ON UPDATE CASCADE
);

CREATE TABLE colors (
                        id VARCHAR(255) PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        image_url VARCHAR(255) NOT NULL
);

CREATE TABLE model_exterior_colors (
                                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       model_id BIGINT NOT NULL,
                                       color_id VARCHAR(255) NOT NULL,
                                       price INT NOT NULL,
                                       CONSTRAINT fk_model_exterior_colors_basic_models FOREIGN KEY (model_id) REFERENCES basic_models (id) ON UPDATE CASCADE,
                                       CONSTRAINT fk_model_exterior_colors_colors FOREIGN KEY (color_id) REFERENCES colors (id) ON UPDATE CASCADE
);

CREATE TABLE model_interior_colors (
                                       id VARCHAR(255) PRIMARY KEY,
                                       basic_model_id BIGINT NOT NULL,
                                       name VARCHAR(255) NOT NULL,
                                       price INT NOT NULL,
                                       image_url VARCHAR(255) NOT NULL,
                                       CONSTRAINT fk_model_interior_colors_basic_models FOREIGN KEY (basic_model_id) REFERENCES basic_models (id) ON UPDATE CASCADE
);

CREATE TABLE trim_exterior_colors (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      trim_id BIGINT NOT NULL,
                                      model_exterior_color_id BIGINT NOT NULL,
                                      CONSTRAINT fk_trim_exterior_colors_trims FOREIGN KEY (trim_id) REFERENCES trims (id) ON UPDATE CASCADE,
                                      CONSTRAINT fk_trim_exterior_colors_model_exterior_colors FOREIGN KEY (model_exterior_color_id) REFERENCES model_exterior_colors (id) ON UPDATE CASCADE
);

CREATE TABLE trim_interior_colors (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      model_interior_color_id VARCHAR(255) NOT NULL,
                                      trim_exterior_color_id BIGINT NOT NULL,
                                      CONSTRAINT fk_trim_interior_colors_model_interior_colors FOREIGN KEY (model_interior_color_id) REFERENCES model_interior_colors (id) ON UPDATE CASCADE,
                                      CONSTRAINT fk_trim_interior_colors_trim_exterior_colors FOREIGN KEY (trim_exterior_color_id) REFERENCES trim_exterior_colors (id)  ON UPDATE CASCADE
);

CREATE TABLE detail_trim_option_interior_color_condition (
                                                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                                             detail_trim_option_id BIGINT NOT NULL,
                                                             trim_interior_color_id BIGINT NOT NULL,
                                                             CONSTRAINT fk_detail_trim_option_interior_color_condition_option FOREIGN KEY (detail_trim_option_id) REFERENCES detail_trim_options (id) ON UPDATE CASCADE,
                                                             CONSTRAINT fk_detail_trim_option_interior_color_condition_color FOREIGN KEY (trim_interior_color_id) REFERENCES trim_interior_colors (id) ON UPDATE CASCADE
);

CREATE TABLE detail_trim_package_interior_color_condition (
                                                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                                              detail_trim_package_id BIGINT NOT NULL,
                                                              trim_interior_color_id BIGINT NOt NULL,
                                                              CONSTRAINT fk_detail_trim_package_interior_color_condition_package FOREIGN KEY (detail_trim_package_id) REFERENCES detail_trim_packages (id) ON UPDATE CASCADE,
                                                              CONSTRAINT fk_detail_trim_package_interior_color_condition_color FOREIGN KEY (trim_interior_color_id) REFERENCES trim_interior_colors (id) ON UPDATE CASCADE
);
