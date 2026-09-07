import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-modal-confirmacao',
  imports: [],
  templateUrl: './modal-confirmacao.html',
  styleUrl: './modal-confirmacao.scss',
})
export class ModalConfirmacao {

  readonly titulo = input<string>('Confirmar exclusão');
  readonly mensagem = input<string>('Deseja realmente continuar?');
  readonly textoConfirmar = input<string>('Excluir');
  readonly processando = input<boolean>(false);
  readonly textoProcessando = input<string>('Processando...');

  readonly confirmar = output<void>();
  readonly cancelar = output<void>();

  

}
