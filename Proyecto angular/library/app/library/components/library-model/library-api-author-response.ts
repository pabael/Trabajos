export interface ApiAuthorResponse {
  photos:          number[];
  key:             string;
  personal_name:   string;
  bio:             string;
  source_records:  string[];
  title:           string;
  name:            string;
  type:            Type;
  remote_ids:      RemoteIDS;
  birth_date:      string;
  death_date:      string;
  links:           Link[];
  alternate_names: string[];
  latest_revision: number;
  revision:        number;
  created:         Created;
  last_modified:   Created;
}

export interface Created {
  type:  string;
  value: Date;
}

export interface Link {
  title: string;
  url:   string;
  type:  Type;
}

export interface Type {
  key: string;
}

export interface RemoteIDS {
  viaf:         string;
  goodreads:    string;
  storygraph:   string;
  isni:         string;
  librarything: string;
  amazon:       string;
  wikidata:     string;
}
