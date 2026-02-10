import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  Button,
  FormControl,
  FormLabel,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Text,
  VStack,
} from "@chakra-ui/react";
import { submitReservation } from "../../redux/features/tableReservationSlice";

export default function ModalReserva({ mesa, turno, onClose }) {
  const dispatch = useDispatch();
  const { selectedDate, loading } = useSelector((state) => state.tableReservation);
  const [nombre, setNombre] = useState("");

  const handleReservar = () => {
    dispatch(
      submitReservation({
        tableId: mesa.id,
        shiftId: turno,
        date: selectedDate,
        customerName: nombre,
      })
    );

    onClose();
  };

  return (
    <Modal isOpen onClose={onClose} isCentered size="sm">
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Reservar {mesa.name}</ModalHeader>
        <ModalCloseButton />
        <ModalBody>
          <VStack spacing={3} align="stretch">
            <Text color="gray.500">Turno seleccionado: {turno}</Text>
            <FormControl>
              <FormLabel>Nombre</FormLabel>
              <Input
                type="text"
                value={nombre}
                onChange={(event) => setNombre(event.target.value)}
                placeholder="Tu nombre"
              />
            </FormControl>
          </VStack>
        </ModalBody>
        <ModalFooter gap={2}>
          <Button variant="ghost" onClick={onClose}>
            Cancelar
          </Button>
          <Button
            colorScheme="orange"
            onClick={handleReservar}
            isLoading={loading}
            loadingText="Confirmando..."
            isDisabled={!nombre}
          >
            Confirmar
          </Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
}
