import { BookDetail } from "./library-book-detail";

export interface AuthorDetail{
  olid:            string;
  name?:            string;
  alternate_names?: string[];
  birth_date?:      string;
  death_date?:     string;
  bio?:             string;
  photo:           string;
  books:           BookDetail[];
}
