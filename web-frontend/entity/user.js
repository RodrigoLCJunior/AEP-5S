// user.js

  export class User {
    constructor({ id, nome, email, senha, cep }) {
      this.id = id;
      this.nome = nome;
      this.email = email;
      this.senha = senha;
      this.cep = cep;
    }
  
    toJson() {
      return {
        nome: this.nome,
        email: this.email,
        senha: this.senha,
        cep: this.cep,
      };
    }
  }
  