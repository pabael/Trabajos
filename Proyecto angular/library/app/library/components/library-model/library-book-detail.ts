export interface BookDetail{
  olid: string;
  imageUrl: string;
  title: string ;
  authorList: {olid: string, name: string}[];
  date: string;
  fav: boolean | undefined;
  type: string;
  subtitle: string;
  description: string;
}
