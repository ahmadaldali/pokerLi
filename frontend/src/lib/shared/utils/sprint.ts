import type { TranslationFunctions } from "$i18n/i18n-types";

export const sprintUtils = () => {
  const getSprintTableColumns = (t: TranslationFunctions) => {
    return [
      { key: "name", label: "Sprint", width: "40%" },
      { key: "creator", label: "Creator", width: "30%" },
      { key: "sequence", label: "Sequence", width: "20%" },
    ];
  }

  const encodeSprintId = (id: string | number) => {
    return btoa(id.toString());
  }
  
  const decodeSprintId = (encodedId: string) => {
    return Number(atob(encodedId));
  }

  return {
    getSprintTableColumns,
    encodeSprintId,
    decodeSprintId,
  };
}