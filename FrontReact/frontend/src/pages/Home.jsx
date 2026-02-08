import { useSearchParams } from "react-router-dom";
import { USER_TYPE, saveToLocalStorage, TABLE_ID } from "../functions/localStorage";
import HomeHero from "../components/home/HomeHero";

export default function Home() {
  const [searchParams] = useSearchParams();

  try {
    const userType = searchParams.get("userType")?.toString().toUpperCase() || "LOCAL";
    const tableId = searchParams.get("tableId") || "-1";

    saveToLocalStorage(USER_TYPE, userType);
    saveToLocalStorage(TABLE_ID, tableId);
  } catch (error) {
    console.error("Error rendering Home page:", error);
  }

  return <HomeHero />;
}
