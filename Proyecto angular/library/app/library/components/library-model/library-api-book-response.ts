export interface ApiBookResponse {
    type:            Type;
    title:           string;
    authors:         Type[] | AuthorElement[];
    publish_date:    string;
    source_records:  string[];
    number_of_pages: number;
    publishers:      string[];
    physical_format: string;
    full_title:      string;
    subtitle:        string;
    works:           Type[];
    key:             string;
    covers:          number[];
    identifiers:     Classifications;
    classifications: Classifications;
    copyright_date:  string;
    local_id:        string[];
    description:     Created;
    isbn_10:         string[];
    isbn_13:         string[];
    latest_revision: number;
    revision:        number;
    created:         Created;
    last_modified:   Created;
}

export interface Type {
    key: string;
}

export interface AuthorElement {
  type?:   Type;
  author: Type;
}

export interface Classifications {
}

export interface Created {
    type:  string;
    value: string;
}
