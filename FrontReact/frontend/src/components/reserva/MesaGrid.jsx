import { Box, SimpleGrid, Text } from "@chakra-ui/react";
import MesaCard from "./MesaCard";

const MesasGrid = ({ mesas, turnoSeleccionado }) => {
    return (
        <Box>
            <Text
                textTransform="uppercase"
                fontSize="xs"
                letterSpacing="widest"
                color="gray.400"
                mb={4}
            >
                Salon principal
            </Text>

            <SimpleGrid columns={{ base: 2, sm: 3, md: 4, lg: 5 }} spacing={5}>
                {mesas.map((mesa) => (
                    <MesaCard key={mesa.id} mesa={mesa} turno={turnoSeleccionado} />
                ))}
            </SimpleGrid>

            <Text mt={4} fontSize="sm" color="gray.500">
                Selecciona una mesa disponible para continuar.
            </Text>
        </Box>
    );
};

export default MesasGrid;
