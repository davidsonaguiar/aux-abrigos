package com.compass.donation;

import com.compass.common.Response;
import com.compass.common.exception.BadRequestException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NoCapacityException;
import com.compass.common.exception.NotFoundException;
import com.compass.donation.dtos.CreateDonationResponseDto;
import com.compass.donation.dtos.DonationDto;
import com.compass.donation.dtos.FindDonationResponseDto;
import com.compass.item.dtos.ItemDto;

import java.util.List;

public class DonationController {
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    public Response<List<CreateDonationResponseDto>> saveMany(List<DonationDto> donations) {
        try {
            List<CreateDonationResponseDto> response = donationService.saveMany(donations);
            return new Response<>(response, "Doações realizadas com sucesso");
        }
        catch (NoCapacityException exception) {
            return new Response<>(null, exception.getMessage());
        }
        catch (NotFoundException exception) {
            return new Response<>(null,"Recurso não encontrado: " + exception.getMessage());
        }
        catch (BadRequestException exception) {
            return new Response<>(null, "Dados inválidos: " + exception.getMessage());
        }
        catch (DaoException exception) {
            return new Response<>(null, "Erro ao salvar doações no banco de dados");
        }
        catch (Exception exception) {
            return new Response<>(null, "Erro desconhedico");
        }
    }

    public Response<CreateDonationResponseDto> save(DonationDto donation) {
        try {
            CreateDonationResponseDto response = donationService.save(donation);
            return new Response<>(response, "Doação realizada com sucesso");
        }
        catch (NotFoundException exception) {
            return new Response<>(null, "Recurso não encontrado: " + exception.getMessage());
        }
        catch (NoCapacityException exception) {
            return new Response<>(null, exception.getMessage());
        }
        catch (BadRequestException exception) {
            return new Response<>(null, "Dados inválidos: " + exception.getMessage());
        }
        catch (DaoException exception) {
            exception.printStackTrace();
            return new Response<>(null, "Erro ao salvar doação no banco de dados");
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new Response<>(null, "Erro desconhedico");
        }
    }

    public Response<List<CreateDonationResponseDto>> listAll() {
        try {
            List<CreateDonationResponseDto> response = donationService.findAll();
            return new Response<>(response, "Doações listadas com sucesso");
        }
        catch (NotFoundException exception) {
            return new Response<>(null, "Recurso não encontrado: " + exception.getMessage());
        }
        catch (BadRequestException exception) {
            return new Response<>(null, "Dados inválidos: " + exception.getMessage());
        }
        catch (DaoException exception) {
            return new Response<>(null, "Erro ao buscar doações no banco de dados");
        }
        catch (Exception exception) {
            return new Response<>(null, "Erro desconhedico");
        }
    }

    public Response<FindDonationResponseDto> findById(Long id) {
        try {
            FindDonationResponseDto response = donationService.findDonationById(id);
            return new Response<>(response, "Doação encontrada");
        }
        catch (NotFoundException exception) {
            return new Response<>(null, "Recurso não encontrado: " + exception.getMessage());
        }
        catch (BadRequestException exception) {
            return new Response<>(null, "Dados inválidos: " + exception.getMessage());
        }
        catch (DaoException exception) {
            return new Response<>(null, "Erro ao buscar doação no banco de dados");
        }
        catch (Exception exception) {
            return new Response<>(null, "Erro desconhedico");
        }
    }

    public Response<CreateDonationResponseDto> updateCenter(Long donationId, Long id) {
        try {
            CreateDonationResponseDto response = donationService.updateCenter(donationId, id);
            return new Response<>(response, "Centro da doação atualizado com sucesso");
        }
        catch (NoCapacityException exception) {
            return new Response<>(null, exception.getMessage());
        }
        catch (NotFoundException exception) {
            return new Response<>(null, "Recurso não encontrado: " + exception.getMessage());
        }
        catch (BadRequestException exception) {
            return new Response<>(null, "Dados inválidos: " + exception.getMessage());
        }
        catch (DaoException exception) {
            return new Response<>(null, "Erro ao atualizar centro da doação no banco de dados");
        }
        catch (Exception exception) {
            return new Response<>(null, "Erro desconhedico");
        }
    }

    public Response<ItemDto> removerItem(Long donationId, Long itemId) {
        try {
            ItemDto response = donationService.removeItem(donationId, itemId);
            return new Response<>(response, "Item removido da doação com sucesso");
        }
        catch (NotFoundException exception) {
            return new Response<>(null, "Recurso não encontrado: " + exception.getMessage());
        }
        catch (BadRequestException exception) {
            return new Response<>(null, "Dados inválidos: " + exception.getMessage());
        }
        catch (DaoException exception) {
            return new Response<>(null, "Erro ao remover item da doação no banco de dados");
        }
        catch (Exception exception) {
            return new Response<>(null, "Erro desconhedico");
        }
    }

    public Response<CreateDonationResponseDto> delete(Long id) {
        try {
            CreateDonationResponseDto response = donationService.delete(id);
            return new Response<>(response, "Doação removida com sucesso");
        }
        catch (NotFoundException exception) {
            return new Response<>(null, "Recurso não encontrado: " + exception.getMessage());
        }
        catch (DaoException exception) {
            return new Response<>(null, "Erro ao remover doação no banco de dados");
        }
        catch (Exception exception) {
            return new Response<>(null, "Erro desconhedico");
        }
    }
}
