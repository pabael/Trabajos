export interface ApiWorksResponse {
  links:   Links;
  size:    number;
  entries: Entry[];
}

export interface Entry {
  type?:            TypeClass;
  title:           string;
  authors:         AuthorElement[];
  key:             string;
  latest_revision?: number;
  revision?:        number;
  created?:         Created;
  last_modified?:   Created;
  covers?:         number[];
  subjects?:       string[];
  description?:    Created;
}

export interface AuthorElement {
  type:   TypeClass;
  author: TypeClass;
}

export interface TypeClass {
  key: string;
}

export interface Created {
  type:  TypeEnum;
  value: string;
}

export enum TypeEnum {
  TypeDatetime = "/type/datetime",
  TypeText = "/type/text",
}

export interface Links {
  self:   string;
  author: string;
}
