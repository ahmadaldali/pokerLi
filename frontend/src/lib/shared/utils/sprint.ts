import type { TranslationFunctions } from "$i18n/i18n-types";

export const sprintUtils = () => {
  const getSprintTableColumns = (t: TranslationFunctions) => {
    return [
      { key: "name", label: "Room", width: "40%" },
      { key: "creator", label: "creator", width: "30%" },
      { key: "sequence", label: "Sequence", width: "20%" },
    ];
  }

  return {
    getSprintTableColumns,
  };
}