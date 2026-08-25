import { CurrencyPipe, DecimalPipe } from '@angular/common';
import { Component, input } from '@angular/core';

@Component({
  selector: 'app-meta-card',
  imports: [
    CurrencyPipe,
    DecimalPipe
  ],
  templateUrl: './meta-card.html',
  styleUrl: './meta-card.scss',
})
export class MetaCard {

  titulo = input.required<string>();
  valorMeta = input.required<number>();
  saldo = input.required<number>();
  percentual = input.required<number>();
  valorRestante = input.required<number>();
  metaAtingida = input.required<boolean>();

}
