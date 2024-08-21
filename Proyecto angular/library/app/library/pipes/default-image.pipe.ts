import { Pipe, PipeTransform } from '@angular/core'

@Pipe({
  name: 'defaultImage'
})

export class DefaultImagePipe implements PipeTransform {
  transform(imageUrl: string, defaultImageUrl: string = 'assets/default.png') {
    return imageUrl ? imageUrl: defaultImageUrl;
  }
}
