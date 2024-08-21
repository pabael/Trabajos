export interface Card{
  olid: string;
  url: string;
  buttons?: button[];
  imageUrl: string;
  title?: string;
  subtitle?: string;
  links?: {name: string, route: string}[];
  span?: string;
}

export interface button{
    name: string,
    clicked: boolean | undefined
}
