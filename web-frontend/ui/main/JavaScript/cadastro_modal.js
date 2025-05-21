import { UserService } from '../../../domain/user_service.js';

document.addEventListener('DOMContentLoaded', () => {
  const registerForm = document.getElementById('register-form');

  if (registerForm) {
    registerForm.addEventListener('submit', async (event) => {
      event.preventDefault();
      
      const nome = document.getElementById('register-name').value.trim();
      const email = document.getElementById('register-email').value.trim();
      const cep = document.getElementById('register-cep').value;
      const senha = document.getElementById('register-password').value.trim();
      const confirmarSenha = document.getElementById('confirm-password').value.trim();
      
      console.log('cep:', cep);
      
      if (!cep) {
        alert('Campo CEP não encontrado!');
        return;
      }
      
      if (senha !== confirmarSenha) {
        alert('As senhas não coincidem!');
        return;
      }

      const emailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
      if (!emailValid) {
        alert('Digite um email válido.');
        return;
      }

      const result = await UserService.register(nome, email, senha, cep);

      if (result.success) {
        alert('Cadastro realizado com sucesso! Agora você pode fazer login.');
        // Você pode fechar o modal e abrir o de login
      } else {
        alert(`Erro no cadastro: ${result.message}`);
      }
    });
  }
});
