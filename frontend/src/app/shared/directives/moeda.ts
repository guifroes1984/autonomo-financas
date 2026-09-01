import {
  Directive,
  ElementRef,
  forwardRef,
  HostListener,
  inject
} from '@angular/core';

import {
  ControlValueAccessor,
  NG_VALUE_ACCESSOR
} from '@angular/forms';

@Directive({
  selector: 'input[appMoeda]',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => Moeda),
      multi: true
    }
  ]
})
export class Moeda implements ControlValueAccessor {

  private readonly elementRef =
    inject(ElementRef<HTMLInputElement>);

  private onChange: (valor: number | null) => void = () => {};
  private onTouched: () => void = () => {};

  @HostListener('input', ['$event'])
  onInput(event: Event): void {
    const input = event.target as HTMLInputElement;

    // Permite somente números e uma vírgula.
    let valorDigitado = input.value
      .replace(/[^\d,]/g, '');

    // Impede mais de uma vírgula.
    const partes = valorDigitado.split(',');

    if (partes.length > 2) {
      valorDigitado =
        partes[0] + ',' + partes.slice(1).join('');
    }

    // Limita os centavos a duas casas.
    if (valorDigitado.includes(',')) {
      const [inteiro, decimal = ''] =
        valorDigitado.split(',');

      valorDigitado =
        inteiro + ',' + decimal.slice(0, 2);
    }

    input.value = valorDigitado;

    if (!valorDigitado) {
      this.onChange(null);
      return;
    }

    const valorNumerico =
      Number(valorDigitado.replace(',', '.'));

    this.onChange(
      Number.isNaN(valorNumerico)
        ? null
        : valorNumerico
    );
  }

  @HostListener('blur')
  onBlur(): void {
    const input = this.elementRef.nativeElement;

    if (input.value) {
      const valor =
        Number(input.value.replace(',', '.'));

      if (!Number.isNaN(valor)) {
        input.value = this.formatar(valor);
        this.onChange(valor);
      }
    }

    this.onTouched();
  }

  writeValue(valor: number | null): void {
    this.elementRef.nativeElement.value =
      valor !== null && valor !== undefined
        ? this.formatar(valor)
        : '';
  }

  registerOnChange(
    fn: (valor: number | null) => void
  ): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(desabilitado: boolean): void {
    this.elementRef.nativeElement.disabled =
      desabilitado;
  }

  private formatar(valor: number): string {
    return valor.toLocaleString('pt-BR', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    });
  }
}