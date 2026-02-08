import { useMemo, useState } from "react";
import { Box, HStack, Text, VStack } from "@chakra-ui/react";
import ModalReserva from "./ModalReserva";

const MesaCard = ({ mesa, turno }) => {
    const [show, setShow] = useState(false);

    const shift = mesa.shifts.find((s) => s.id.shiftId === turno);
    const estado = shift?.state?.id;
    const isAvailable = estado === 1;

    const seatDots = useMemo(() => {
        const count = Math.min(mesa.chairsAmount || 0, 8);
        return Array.from({ length: count }, (_, idx) => idx);
    }, [mesa.chairsAmount]);

    return (
        <>
            <Box
                role="button"
                onClick={() => isAvailable && setShow(true)}
                cursor={isAvailable ? "pointer" : "not-allowed"}
                bg="white"
                borderWidth="1px"
                borderColor={isAvailable ? "orange.200" : "gray.200"}
                rounded="2xl"
                p={4}
                minH="120px"
                boxShadow={isAvailable ? "sm" : "none"}
                transition="all 0.2s"
                _hover={isAvailable ? { borderColor: "orange.400", boxShadow: "md" } : undefined}
            >
                <VStack spacing={2} align="stretch">
                    <Text fontWeight="semibold">{mesa.name}</Text>
                    <Text fontSize="sm" color="gray.500">
                        {mesa.chairsAmount} comensales
                    </Text>
                    <HStack spacing={1} justify="space-between">
                        <Box
                            w="52px"
                            h="52px"
                            rounded="full"
                            borderWidth="1px"
                            borderColor={isAvailable ? "orange.300" : "gray.300"}
                            bg={isAvailable ? "orange.50" : "gray.100"}
                        />
                        <HStack spacing={1} wrap="wrap" maxW="76px">
                            {seatDots.map((dot) => (
                                <Box
                                    key={dot}
                                    w="8px"
                                    h="8px"
                                    rounded="full"
                                    bg={isAvailable ? "orange.400" : "gray.400"}
                                />
                            ))}
                        </HStack>
                    </HStack>
                </VStack>
            </Box>

            {show && (
                <ModalReserva
                    mesa={mesa}
                    turno={turno}
                    onClose={() => setShow(false)}
                />
            )}
        </>
    );
};

export default MesaCard;
