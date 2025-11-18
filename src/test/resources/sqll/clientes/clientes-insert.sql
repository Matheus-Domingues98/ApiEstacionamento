insert into usuarios (usuario_id, username, password, role)
    values(100, 'ana@email.com', '$2a$12$VcbSUniUDv5fehd.BiMT2.kF2dVxCmuYLzxkAsGN72Z/Gb1RprIVO', 'ROLE_ADMIN'), --123456
          (101, 'mariana@email.com', '$2a$12$o6XLB8yeIK8q7Ey6F30b8edOgblMhk0XHT2cJSwfMZIh1bw9P/S0W', 'ROLE_CLIENTE'), --343434
          (102, 'joaozinho@email.com', '$2a$12$9AfZBzKIHW/q9ptye6OVOuUC2U7VCyAOpeVZZ1DXsf5RE6P7N25jC', 'ROLE_CLIENTE'),--333333
          (103, 'tom@email.com', '$2a$12$9AfZBzKIHW/q9ptye6OVOuUC2U7VCyAOpeVZZ1DXsf5RE6P7N25jC', 'ROLE_CLIENTE');--333333



insert into CLIENTES (id, nome, cpf, id_usuario)
    values (10, 'Mariana Silva', '90181152070', 101 ),
           (11, 'João Alves', '62421967007', 102);