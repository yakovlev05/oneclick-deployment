package ru.yakovlev05.infra.docker.service;

import org.springframework.stereotype.Service;
import ru.yakovlev05.infra.consts.GlobalConst;
import ru.yakovlev05.infra.docker.dto.DomainBindingInfoDto;

import java.util.HashMap;
import java.util.Map;

import static ru.yakovlev05.infra.consts.GlobalConst.SUFFIX_DOMAIN;
import static ru.yakovlev05.infra.consts.GlobalConst.TRAEFIK_NETWORK;

@Service
public class TraefikLabelsService {

    public Map<String, String> getLabels(Long deploymentId, DomainBindingInfoDto dto) {
        String router = "traefik.http.routers." + GlobalConst.TRAEFIK_NAMESPACE + "-" + deploymentId;
        String service = "traefik.http.services." + GlobalConst.TRAEFIK_NAMESPACE + "-" + deploymentId;

        Map<String, String> labels = new HashMap<>();
        labels.put("traefik.enable", "true");
        labels.put("traefik.docker.network", TRAEFIK_NETWORK);
        labels.put(router + ".rule", "HostRegexp(`(.+\\\\.)?%s$`)".formatted(dto.getMainSubdomain() + "." + SUFFIX_DOMAIN));
        labels.put(router + ".entrypoints", "websecure");
        labels.put(router + "..tls.certresolver", "cloudflare");
        labels.put(router + ".tls", "true");
        labels.put(service + ".loadbalancer.server.port", String.valueOf(dto.getContainerPort()));

        for (int i = 0; i < dto.getSubdomains().size(); i++) {
            String sub = dto.getSubdomains().get(i) + "." + SUFFIX_DOMAIN;
            labels.put(router + ".tls.domains[%d].main".formatted(i), sub);
            labels.put(router + ".tls.domains[%d].sans".formatted(i), "*." + sub);
        }

        return labels;
    }

}
